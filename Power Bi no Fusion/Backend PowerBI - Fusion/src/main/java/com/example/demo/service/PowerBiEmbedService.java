package com.example.demo.service;

import com.example.demo.config.PowerBiProperties;
import com.example.demo.dto.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PowerBiEmbedService {

    private final PowerBiProperties props;
    private final RestClient http = RestClient.create();

    public PowerBiEmbedService(PowerBiProperties props) {
        this.props = props;
    }

    public EmbedConfigResponse getEmbedConfig() {
        String accessToken = acquireAccessToken();
        String embedUrl = getEmbedUrl(accessToken);
        GenerateTokenResponse embed = generateEmbedToken(accessToken);

        return new EmbedConfigResponse(
                props.reportId(),
                embedUrl,
                embed.token(),
                embed.expiration()
        );
    }

    private String acquireAccessToken() {
        var form = new LinkedMultiValueMap<String, String>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", props.clientId());
        form.add("client_secret", props.clientSecret());
        form.add("scope", "https://analysis.windows.net/powerbi/api/.default");

        String url = "https://login.microsoftonline.com/" + props.tenantId() + "/oauth2/v2.0/token";

        AadTokenResponse res = http.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(AadTokenResponse.class);

        if (res == null || res.accessToken() == null || res.accessToken().isBlank()) {
            throw new IllegalStateException("Falha ao obter access_token do Entra.");
        }
        return res.accessToken();
    }

    private String getEmbedUrl(String accessToken) {
        String url = "https://api.powerbi.com/v1.0/myorg/groups/" + props.workspaceId()
                + "/reports/" + props.reportId();

        @SuppressWarnings("unchecked")
        Map<String, Object> report = http.get()
                .uri(url)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(Map.class);

        Object embedUrl = report == null ? null : report.get("embedUrl");
        if (embedUrl == null) {
            throw new IllegalStateException("Não consegui obter embedUrl do report.");
        }
        return embedUrl.toString();
    }

    private GenerateTokenResponse generateEmbedToken(String accessToken) {
        var req = new GenerateTokenRequestV2(
                List.of(new GenerateTokenRequestV2.IdRef(props.datasetId())),
                List.of(new GenerateTokenRequestV2.IdRef(props.reportId())),
                List.of(new GenerateTokenRequestV2.IdRef(props.workspaceId()))
        );

        GenerateTokenResponse res = http.post()
                .uri("https://api.powerbi.com/v1.0/myorg/GenerateToken")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(req)
                .retrieve()
                .body(GenerateTokenResponse.class);

        if (res == null || res.token() == null || res.token().isBlank()) {
            throw new IllegalStateException("Falha ao gerar embed token.");
        }
        return res;
    }
}
