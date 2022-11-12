package seolnavy.order.common.init;

import static seolnavy.order.domain.item.ItemCommand.RegisterItemRequest;

import java.util.List;
import javax.annotation.PostConstruct;
import seolnavy.order.application.ItemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class InitData {

	private final ItemFacade itemFacade;

	@PostConstruct
	public void initData() {
		final var itemRequests = List.of(
				RegisterItemRequest.of(768848L, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000L, 45L),
				RegisterItemRequest.of(748943L, "디오디너리 데일리 세트 (Daily set)", 19000L, 89L),
				RegisterItemRequest.of(779989L, "버드와이저 HOME DJing 굿즈 세트", 35000L, 43L),
				RegisterItemRequest.of(779943L, "Fabrik Pottery Flat Cup & Saucer - Mint", 24900L, 89L),
				RegisterItemRequest.of(768110L, "네페라 손 세정제 대용량 500ml 이더블유지", 7000L, 79L),
				RegisterItemRequest.of(517643L, "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800L, 26L),
				RegisterItemRequest.of(706803L, "ZEROVITY™ Flip Flop Cream 2.0 (Z-FF-CRAJ-)", 38000L, 81L),
				RegisterItemRequest.of(759928L, "마스크 스트랩 분실방지 오염방지 목걸이", 2800L, 85L),
				RegisterItemRequest.of(213341L, "20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)", 33250L, 99L),
				RegisterItemRequest.of(377169L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 24900L, 60L),
				RegisterItemRequest.of(744775L, "SHUT UP [TK00112]", 28000L, 35L),
				RegisterItemRequest.of(779049L, "[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)", 10000L, 64L),
				RegisterItemRequest.of(611019L, "플루크 new 피그먼트 오버핏 반팔티셔츠 FST701 / 7color M", 19800L, 7L),
				RegisterItemRequest.of(628066L, "무설탕 프로틴 초콜릿 틴볼스", 12900L, 8L),
				RegisterItemRequest.of(502480L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900L, 41L),
				RegisterItemRequest.of(782858L, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500L, 50L),
				RegisterItemRequest.of(760709L, "파버카스텔 연필1자루", 200L, 70L),
				RegisterItemRequest.of(778422L, "캠핑덕 우드롤테이블", 45000L, 7L),
				RegisterItemRequest.of(648418L, "BS 02-2A DAYPACK 26 (BLACK)", 238000L, 5L)
		);
		itemRequests.forEach(itemFacade::registerItem);
	}

}
