import SwiftUI
import Shared

struct BadHabitScreen: View {
	
	let onEvent: (BadHabitEvent) -> Void
	
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
			title: "Bad Habits",
			actionContent: {
				TopBarActionButton(
					imageName: "trash",
					onClick: { onEvent(.DeleteHabit())}
				)
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			Text("Hello bad Habit")
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	BadHabitScreen(onEvent: { _ in })
}
