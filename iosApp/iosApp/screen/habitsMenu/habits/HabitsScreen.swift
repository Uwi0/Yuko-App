import SwiftUI
import Shared

struct HabitsScreen: View {
	
	@Binding var state: HabitsState
	let onEvent: (HabitsEvent) -> Void
	
	var body: some View {
		VStack {
			TopAppBarView()
			Divider()
			ContentView()
		}
	}
	
	@ViewBuilder
	private func TopAppBarView() -> some View {
		NavigationTopAppbar(
			title: "Habits",
			actionContent: {
				CreateHabitButton()
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func CreateHabitButton() -> some View {
		Image(systemName: "pencil")
			.resizable()
			.scaledToFit()
			.frame(width: 24, height: 24)
			.onTapGesture {
				onEvent(.TapToAddHabit())
			}
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack {
			HabitListView(habits: state.habits, onEvent: onEvent)
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
	}
}

#Preview {
	HabitsScreen(state: .constant(.companion.default()), onEvent: { _ in })
}
