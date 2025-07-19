import SwiftUI
import Shared

struct CompletionView: View {
	
	@Binding var state: GoodHabitState
	let onEvent: (GoodHabitEvent) -> Void
	@State private var dummyData: [Double] = [
		0.3, 0.6, 0.8, 0.2, 0.5, 0.7, 1
	]
	
	var body: some View {
		VStack {
			TitleDateComponentView()
			switch state.completionViewMode {
			case .weekly: CompletionWeeklyView()
			case .monthly: CompletionMonthlyView()
			case .yearly: CompletionYearlyView()
			}
		}
	}
	
	private var icon: String {
		state.completionViewMode.toIcon()
	}
	
	@ViewBuilder
	private func TitleDateComponentView() -> some View {
		HStack(alignment: .center, spacing: 8) {
			Text("Start Date: \(state.goodHabit.startDate)")
				.font(Typography.titleMedium)
			
			Spacer()
			
			Button {
				onEvent(.ChangeCompletionMode(mode: state.completionViewMode))
			} label : {
				Image(icon)
					.resizable()
					.scaledToFit()
					.frame(width: 24, height: 24)
			}
		}
		
	}
	
	@ViewBuilder
	private func CompletionWeeklyView() -> some View {
		VStack {
			HorizontalCalendarStripView(calendarEffect: { _ in })
			WeekChartView(data: dummyData)
		}
	}
	
	@ViewBuilder
	private func CompletionMonthlyView() -> some View {
		CalendarMonthView()
	}
	
	@ViewBuilder
	private func CompletionYearlyView() -> some View {
		let completionYear = CompletionYearStore()
		let completionValue = completionYear.generateCompletionYear(year: 2025, completionData: [:])
		CompletionHeatMapView(completionYear: completionValue)
	}
}

#Preview {
	CompletionView(state: .constant(.companion.default()), onEvent: { _ in })
}
