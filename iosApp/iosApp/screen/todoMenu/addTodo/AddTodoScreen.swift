import SwiftUI
import Shared

struct AddTodoScreen: View {
	
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
			onAction: { onEvent(.NavigateBack()) }
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			Text("Hello from Add Todo")
		}
	}
}

#Preview {
	AddTodoScreen(onEvent: { _ in })
}
