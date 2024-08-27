package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dto.TransferDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestClientTransferService {
    private static final String ENDPOINT = "http://localhost:8080/api/tenmo/transfer/";

    private final RestTemplate restTemplate;

    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2bGFkaW1pciIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE3MjQ4MzIwMDR9.W6_rtLWs0VpIuu33bc2hpYLJaZXoKUOjGFz1tikdyNeSIUpqI5LUzUAEswcOPijt2Hm5xdZR6jTB5WP2VJSn9A";

    public RestClientTransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestClientTransferService(){
        this.restTemplate = new RestTemplate();
    }

    public Optional<TransferDto> getTransferById(int transferId){
        StringBuilder url = new StringBuilder(ENDPOINT);
        url.append(transferId);

        try {

            ResponseEntity<TransferDto> response= restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    getEntityWithBearer(),
                    TransferDto.class);

            return Optional.ofNullable(response.getBody());

        } catch (RestClientException e) {
            System.out.println("\nError connecting to service. " + e.getMessage());
        }catch (Exception e){
            System.out.println("\nError: " + e.getMessage());
        }
        return Optional.empty();
    }

    // no idea what a 'ParameterizedTypeReference' is.
    // https://stackoverflow.com/questions/63281734/how-to-use-resttemplate-to-get-result-in-list-and-put-the-response-into-list

    public List<TransferDto> getPendingTranfers(int accountId, int transferStatusId){

        String url = String.format("%s/transfer-status/%d/%d", ENDPOINT, accountId, transferStatusId);
        //http://localhost:8080/api/tenmo/transfer/transfer-status/2001/1

        try {
            ResponseEntity<List<TransferDto>> response= restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getEntityWithBearer(),
                    new ParameterizedTypeReference<List<TransferDto>>() {});


            return response.getBody();

        }catch (RestClientException restClientException){
            System.out.println("Error: " + restClientException.getMessage());
            return List.of();
        }
    }

    public HttpEntity getEntityWithBearer(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);

        return new HttpEntity(httpHeaders);
    }

    public static void main(String[] args) {
        HttpEntity entity = new RestClientTransferService().getEntityWithBearer();
        RestClientTransferService tester = new RestClientTransferService();
        //System.out.println(new RestClientTransferService().getTransferById(3001).get().getAmount());
        tester.getPendingTranfers(2002, 2).forEach(
                transferDto -> System.out.println("Transfer :" + transferDto.getAmount() + " "  + transferDto.getTransferStatusId() + " " + transferDto.getSenderAccountId() + " " + transferDto.getRecipientAccountId())
        );
    }

}
