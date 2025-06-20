import SwiftUI
import Shared

struct TodosScreen: View {
	
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
			Text("Empty Todos")
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	TodosScreen(onEvent: { _ in })
}
