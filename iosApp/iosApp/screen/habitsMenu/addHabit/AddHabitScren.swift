import SwiftUI
import Shared

struct AddHabitScren: View {
	
	let onEvent: (AddHabitEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppbarView()
			Divider()
			ContentView()
		}
	}
	
	@ViewBuilder
	private func TopAppbarView() -> some View {
		NavigationTopAppbar(title: "Add Habit", onAction: { onEvent(.NavigateBack())})
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			Text("Hello world")
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	AddHabitScren(onEvent: { _ in })
}
