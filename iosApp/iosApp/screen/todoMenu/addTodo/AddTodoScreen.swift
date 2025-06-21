import SwiftUI
import Shared

struct AddTodoScreen: View {
	
	@Binding var state: AddTodoState
	let onEvent: (AddTodoEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbarView()
			Divider()
			ContentView()
			Spacer()
		}
	}
	
	@ViewBuilder
	private func TopAppbarView() -> some View {
		NavigationTopAppbar(
			title: "Add Todo",
			actionContent: {
				Image(systemName: "square.and.arrow.down")
					.resizable()
					.scaledToFit()
					.frame(width: 24, height: 24)
					.onTapGesture { onEvent(.SaveTodo()) }
			},
			onAction: { onEvent(.NavigateBack()) }
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			TextField(
				"Title",
				text: Binding(
					get: { state.title },
					set: { value in onEvent(.ChangedTitle(title: value)) }
				)
			)
			.font(Typography.titleMedium)
			
			TextField(
				"Description",
				text: Binding(
					get: { state.description_ },
					set: { value in onEvent(.ChangedDescription(description: value)) }
				)
			)
			.font(Typography.bodyMedium)
			.foregroundStyle(ColorTheme.outline)
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	AddTodoScreen(state: .constant(.companion.default()),onEvent: { _ in })
}
