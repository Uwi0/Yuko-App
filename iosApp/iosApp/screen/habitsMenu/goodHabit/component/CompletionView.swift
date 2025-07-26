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
			ZStack {
				Group {
					switch state.completionViewMode {
					case .weekly: CompletionWeeklyView()
					case .monthly: CompletionMonthlyView()
					case .yearly: CompletionYearlyView()
					}
				}
				.transition(
					.opacity
						.combined(with: .scale(scale: 0.95))
						.combined(with: .offset(y: 10))
				)
			}
			.animation(.easeInOut(duration: 0.35), value: state.completionViewMode)
		}
	}
	
	private var icon: String {
		state.completionViewMode.toIcon()
	}
	
	@ViewBuilder
	private func TitleDateComponentView() -> some View {
		HStack(alignment: .center, spacing: 8) {
			Text("Start Date: \(state.goodHabit.formattedStartDate)")
				.font(Typography.titleMedium)
			
			Spacer()
			
			Button {
				withAnimation(.easeInOut(duration: 0.35)) {
					onEvent(.ChangeCompletionMode(mode: state.completionViewMode))
				}
			} label : {
				Image(icon)
					.resizable()
					.scaledToFit()
					.frame(width: 24, height: 24)
					.scaleEffect(state.completionViewMode == .yearly ? 1.1 : 1.0)
					.animation(
						.spring(
							response: 0.4,
							dampingFraction: 0.7),
						value: state.completionViewMode
					)
			}
		}
		
	}
	
	@ViewBuilder
	private func CompletionWeeklyView() -> some View {
		VStack {
			HorizontalCalendarStripView(
				currentDate: state.formattedDate,
				weeks: state.allWeeks,
				canScrolledRight: state.canScrolledRightHorizontalDate,
				canScrolledLeft: state.canScrolledLeftHorizontalDate,
				onUpdatedIndex: { index in onEvent(.UpdateWeek(index: index)) }
			)
			WeekChartView(data: state.weeksValue)
		}
	}
	
	@ViewBuilder
	private func CompletionMonthlyView() -> some View {
		CalendarMonthView(
			currentDate: state.formattedDate,
			months: state.allMonths,
			completionMonths: state.completionMonths,
			canScrolledRight: state.canScrolledRightCalendar,
			canScrolledLeft: state.canScrolledLeftHorizontalDate,
			onUpdatedIndex: { index in onEvent(.UpdateMonth(index: index)) }
		)
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
