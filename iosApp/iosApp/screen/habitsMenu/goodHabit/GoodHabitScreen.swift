import SwiftUI
import Shared

struct GoodHabitScreen: View {
	
	@Binding var state: GoodHabitState
	let onEvent: (GoodHabitEvent) -> Void
	@State private var dummyData: [Double] = [
		0.3, 0.6, 0.8, 0.2, 0.5, 0.7, 1
	]
	
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
				TitleDateComponentView()
				HorizontalCalendarStripView(calendarEffect: observeCalendar(effect:))
				WeekChartView(data: dummyData)
			}
			.padding(.horizontal, 16)
			.padding(.vertical, 24)
			.frame(maxWidth: 700, alignment: .leading)
		}
		
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
	
	@ViewBuilder
	private func TitleDateComponentView() -> some View {
		HStack(alignment: .center, spacing: 8) {
			Text("Start Date: \(state.goodHabit.startDate)")
				.font(Typography.titleMedium)
			Spacer()
			Image(AssetIconApp.displayDateWeekly)
				.resizable()
				.scaledToFit()
				.frame(width: 24, height: 24)
		}
	}
	
	private func observeCalendar(effect: HorizontalCalendarEffect) {
		switch onEnum(of: effect) {
		case let .weekChanged(weeks):
			print("week changed! generating new random data")
			dummyData = generateRandomData()
		}
	}
	
	private func generateRandomData() -> [Double] {
		(0..<7).map { _ in Double.random(in: 0.2...1.0) }
	}
	
}


#Preview {
	let state = GoodHabitState(
		loading: false,
		goodHabit:  dummyGoodHabit
	)
	GoodHabitScreen(
		state: .constant(state),
		onEvent: { _ in }
	)
}
