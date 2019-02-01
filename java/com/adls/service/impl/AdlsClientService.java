package com.adls.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microsoft.azure.datalake.store.ADLStoreClient;

@Service
public class AdlsClientService {

@Autowired
private AdlsTokenService adlsAuthService;

@Value("${accountFQDN}")
private String accountFQDN;

public ADLStoreClient getAdlsStoreClient() throws IOException {
	return ADLStoreClient.createClient(accountFQDN, adlsAuthService.getAccessTokenProvider());

}

}
