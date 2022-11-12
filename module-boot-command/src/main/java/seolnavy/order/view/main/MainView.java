package seolnavy.order.view.main;

import java.util.Scanner;
import seolnavy.order.view.View;
import seolnavy.order.view.order.OrderView;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainView implements View {

	private final Scanner scan = new Scanner(System.in);

	private final ApplicationContext context;
	private final OrderView orderView;

	public void show() {
		while (true) {
			final String command = inputCommand();
			if (isOrder(command)) {
				orderView.show();
			} else if (isQuit(command)) {
				System.out.println("고객님의 주문 감사합니다.");
				break;
			}
		}
		SpringApplication.exit(context);
	}

	private String inputCommand() {
		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
		return scan.nextLine();
	}

	private static boolean isOrder(final String command) {
		return "o".equals(command) || "order".equals(command);
	}

	private static boolean isQuit(final String command) {
		return "q".equals(command) || "quit".equals(command);
	}

}
