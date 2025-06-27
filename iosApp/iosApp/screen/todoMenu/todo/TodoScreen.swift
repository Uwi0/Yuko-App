import SwiftUI
import Shared

struct TodoScreen: View {
	
	@Binding var state: TodoState
	let onEvent: (TodoEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbarView()
			Divider()
			ContentView()
		}
	}
	
	@ViewBuilder
	private func TopAppbarView() -> some View {
		NavigationTopAppbar(
			title: "Todo",
			actionContent: {
				EditButtonView()
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func EditButtonView() -> some View {
		Image(systemName: "pencil")
			.resizable()
			.scaledToFit()
			.frame(width: 24, height: 24)
			.onTapGesture {
				onEvent(.TapToEditTodo())
			}
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			Text(state.title)
				.font(Typography.titleMedium)
			Text(state.description_)
				.font(Typography.bodyMedium)
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	TodoScreen(state: .constant(.companion.default()),onEvent: { _ in })
}
