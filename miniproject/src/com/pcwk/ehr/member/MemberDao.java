package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	private static final String MEMBER_FILE = "members.txt";
	private Map<String, Member> members;

	public MemberDao() {
		members = new HashMap<>();
		loadMembers();
	}

	// 회원가입
	public void registerMember(Member newMember) {
		if (members.containsKey(newMember.getUserId())) {
			System.out.println("이미 존재하는 아이디입니다.");
		} else {
			members.put(newMember.getUserId(), newMember);
			saveMembers();
			System.out.println("회원가입이 완료되었습니다.");
		}
	}

	// 로그인
	public Member login(String userId, String password) {
		Member member = members.get(userId);
		if (member != null && member.getPassword().equals(password)) {
			System.out.println("로그인 성공!");
			return member;
		} else {
			System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
			return null;
		}
	}

	// 로그아웃
	public void logout(Member member) {
		System.out.println(member.getName() + "님이 로그아웃하셨습니다.");
	}

	// 회원 데이터 파일로 저장
	private void saveMembers() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_FILE))) {
			for (Member member : members.values()) {
				writer.write(member.getUserId() + "," + member.getPassword() + "," + member.getName());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("회원 데이터를 저장하는 중 오류 발생: " + e.getMessage());
		}
	}

	// 회원 데이터 파일에서 불러오기
	private void loadMembers() {
		try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] memberData = line.split(",");
				String userId = memberData[0];
				String password = memberData[1];
				String name = memberData[2];
				members.put(userId, new Member(userId, password, name));
			}
		} catch (IOException e) {
			System.out.println("회원 데이터를 불러오는 중 오류 발생: " + e.getMessage());
		}
	}
}
