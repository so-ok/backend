package com.sook.backend.pill.repository;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.AbstractSoOkTest;
import com.sook.backend.pill.dto.PillSearchDto;
import com.sook.backend.pill.model.Pill;

@Transactional
class PillRepositoryTest extends AbstractSoOkTest {

	@Autowired
	PillRepository pillRepository;

	@Test
	@DisplayName("search() 테스트 - name")
	public void testSearchByName() throws Exception {
		//given
		PillSearchDto search = PillSearchDto.builder()
				.name("비오틴")
				.build();

		//when
		List<Pill> pills = pillRepository.search(search);

		//then
		assertEquals(27, pills.size());
		pills.forEach((pill -> {
			assertTrue(pill.name().contains(search.name()));
		}));
	}

	@Test
	@DisplayName("search() 테스트 - attention")
	public void testSearchByAttention() throws Exception {
		//given
		List<String> attentions = List.of("장 건강", "간 건강");
		PillSearchDto search = PillSearchDto.builder()
				.attentions(attentions)
				.build();

		//when
		List<Pill> pills = pillRepository.search(search);

		//then
		pills.forEach((pill -> {
			pill.attentionPills().forEach((attentionPill -> {
				anyOf(search.attentions()
						.stream()
						.map(attention -> attentionPill.attention().name().contains(attention))
						.toList());
			}));
		}));

		assertEquals(1141, pills.size());
	}
}
