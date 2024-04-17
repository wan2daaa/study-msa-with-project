package me.wane.money.adapter.in.web;

import java.util.List;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberMoneyListByMembershipIdsCommand {

    private List<String> membershipIds;

}