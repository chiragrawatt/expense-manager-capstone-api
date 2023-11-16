package com.chubb.expensemanager.payloads.reponses;

import java.util.List;

import com.chubb.expensemanager.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
	private String token;
	private User user;
}
