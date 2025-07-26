import SwiftUI
import Shared

struct GoodHabitScreen: View {
	
	@Binding var state: GoodHabitState
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
	private func ContentView() -> some View {
		ScrollView {
			VStack(alignment: .leading, spacing: 16) {
				TitleComponentView()
				GoodHabitMetricsView(habit: state.goodHabit)
				CompletionView(state: $state, onEvent: onEvent)
			}
			.padding(.horizontal, 16)
			.padding(.vertical, 24)
			.frame(maxWidth: 700, alignment: .leading)
		}
		.scrollIndicators(.hidden)
		
	}
	
	@ViewBuilder
	private func TitleComponentView() -> some View {
		VStack(alignment: .leading, spacing: 8) {
			Text(state.goodHabit.name)
				.font(Typography.titleLarge)
			Text(state.goodHabit.description_)
				.font(Typography.bodyMedium)
		}
	}
	
}

