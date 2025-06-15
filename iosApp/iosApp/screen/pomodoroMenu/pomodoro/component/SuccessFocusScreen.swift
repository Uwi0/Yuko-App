
import SwiftUI

struct SuccessFocusScreen: View {
	
	let onFinish: () -> Void
	let onBreak: () -> Void
	let backToPomodoro: () -> Void
	
	private static let BUTTON_WIDTH: CGFloat = 168
	
	var body: some View {
		VStack(alignment: .center, spacing: 16) {
			TopActionView()
			Spacer()
			Text("Wow.. it's really great to be able to focus all this time, do you want to take a break or finish ?")
			ActionButton()
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
	
	@ViewBuilder
	private func TopActionView() -> some View {
		HStack {
			Spacer()
			Image(systemName: "xmark.circle.fill")
				.onTapGesture {
					onFinish()
				}
				
		}
	}
	
	@ViewBuilder
	private func ActionButton() -> some View {
		HStack(spacing: 16) {
			FilledButtonView(
				onClick: onBreak,
				content: {
					Text("Take a break")
				}
			)
			.frame(width: Self.BUTTON_WIDTH)
			
			OutlinedButtonView(
				onClick: backToPomodoro,
				content: {
					Text("Back to Pomodoro")
				}
			)
			.frame(width: Self.BUTTON_WIDTH)
		}
	}
}

#Preview {
	SuccessFocusScreen(onFinish: {}, onBreak: {}, backToPomodoro: {})
}
