package me.wane.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFirmbankingRequest {
    private String firmbankingRequestAggregateIdentifier;
    private int status;

    // 금액, 등 다양한 정보를 변경 할 수 있음
    // status만 변경한다고 가정
}
