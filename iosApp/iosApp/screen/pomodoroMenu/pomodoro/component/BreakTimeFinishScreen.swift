import SwiftUI

struct BreakTimeFinishScreen: View {
	
	let onRetry: () -> Void
	let onFinish: () -> Void
	
	private static let BUTTON_WIDTH: CGFloat = 168
	
	var body: some View {
		VStack(spacing: 16) {
			Text("Nice. Your break time is over, lets go again")
			ActionButton()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
	
	@ViewBuilder
	private func ActionButton() -> some View {
		HStack {
			FilledButtonView(
				onClick: onRetry,
				content: { Text("Start Again")
				}
			)
			.frame(width: Self.BUTTON_WIDTH)
			
			OutlinedButtonView(
				onClick: onFinish,
				content: { Text("Finish")}
			)
			.frame(width: Self.BUTTON_WIDTH)
		}
	}
}

#Preview {
	BreakTimeFinishScreen(onRetry: {}, onFinish: {})
}
