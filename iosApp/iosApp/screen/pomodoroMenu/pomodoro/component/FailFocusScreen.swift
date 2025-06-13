import SwiftUI

struct FailFocusScreen: View {
	
	let onRetry: () -> Void
	let onFinish: () -> Void
	
	private static let BUTTON_WIDTH: CGFloat = 168
	
	var body: some View {
		VStack(spacing: 16) {
			Text("It's okay, try to be more focus and comitment next time")
			ActionButton()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
	
	@ViewBuilder
	private func ActionButton() -> some View {
		HStack(spacing: 16) {
			FilledButtonView(
				onClick: onRetry,
				content: { Text("Try again") }
			)
			.frame(maxWidth: Self.BUTTON_WIDTH)
			
			OutlinedContentButtonView(
				onClick: onFinish,
				content: { Text("Finish")}
			)
			.frame(width: Self.BUTTON_WIDTH)
				
		}
	}
}

#Preview {
	FailFocusScreen(onRetry: {}, onFinish: {})
}
