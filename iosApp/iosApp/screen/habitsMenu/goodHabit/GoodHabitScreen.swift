import SwiftUI
import Shared

struct GoodHabitScreen: View {
	
	let onEvent: (GoodHabitEvent) -> Void
	
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
			title: "Good Habit",
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
	private func DeleteIconButton() -> some View {
		
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			Text("Hello good habit")
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	GoodHabitScreen(onEvent: { _ in })
}
