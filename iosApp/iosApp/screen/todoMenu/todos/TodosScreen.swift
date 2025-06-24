import SwiftUI
import Shared

struct TodosScreen: View {
	
	@Binding var state: TodosState
	let onEvent: (TodosEvent) -> Void
	
	var body: some View {
		VStack {
			TopbarView()
			Divider()
			ContentView()
			Spacer()
		}
	}
	
	@ViewBuilder
	private func TopbarView() -> some View {
		NavigationTopAppbar(
			title: "Todos",
			actionContent: {
				Image(systemName: "square.and.pencil")
					.resizable()
					.scaledToFit()
					.frame(width: 24, height: 24)
					.onTapGesture { onEvent(.TapToAddTodo()) }
			},
			onAction: { onEvent(.NavigateBack()) }
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			TodosListView(todos: state.todos, onEvent: onEvent)
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	TodosScreen(state: .constant(.companion.default()),onEvent: { _ in })
}
