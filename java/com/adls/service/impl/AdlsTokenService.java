package com.adls.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microsoft.azure.datalake.store.oauth2.AccessTokenProvider;
import com.microsoft.azure.datalake.store.oauth2.ClientCredsTokenProvider;

@Service
public class AdlsTokenService {

@Value("${clientId}")
private String clientId;

@Value("${authTokenEndpoint}")
private String authTokenEndpoint;

@Value("${clientKey}")
private String clientKey;

public AccessTokenProvider getAccessTokenProvider() throws IOException {
	AccessTokenProvider provider = new ClientCredsTokenProvider(authTokenEndpoint, clientId, clientKey);

	System.out.println("Access Token: " + provider.getToken().accessToken);
	return provider;
}

}
