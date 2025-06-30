import SwiftUI
import Shared

struct ReminderHabitView: View {
	
	@Binding var state: AddHabitState
	let onEvent: (AddHabitEvent) -> Void
	
	var body: some View {
		VStack(alignment: .leading,spacing: 16) {
			if state.isToggleOn {
				SelectedDayView()
				RecurringDayView()
			}
		}
	}
	
	@ViewBuilder
	private func SelectedDayView() -> some View {
			HStack(spacing: 4) {
					Text("Reminder Days : ")
					ForEach(state.selectedDays, id: \.ordinal) { day in
							Text(day.title).font(Typography.bodyMedium)
					}
			}
	}
	
	@ViewBuilder
	private func RecurringDayView() -> some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack {
				ForEach(ReminderDays.allCases, id: \.ordinal) { day in
					let selected = state.selectedDays.contains(day)
					ButtonDayReminder(
						day: day,
						selected: selected,
						onClick: { }
					)
				}
			}
		}
	}
	
	
	@ViewBuilder
	private func ButtonDayReminder(day: ReminderDays, selected: Bool, onClick: @escaping () -> Void) -> some View {
		Button(action: onClick) {
			Text(day.title)
				.font(Typography.titleMedium)
				.padding(.horizontal, 12)
				.padding(.vertical, 8)
				.background(selected ? ColorTheme.secondaryContainer : ColorTheme.surface)
				.foregroundStyle(selected ? ColorTheme.primary: ColorTheme.secondary)
				.clipShape(RoundedRectangle(cornerRadius: 8))
				.overlay(
					RoundedRectangle(cornerRadius: 8)
						.stroke(selected ? ColorTheme.secondary : ColorTheme.outline, lineWidth: 1)
				)
		}
		.buttonStyle(PlainButtonStyle())
		
	}
}
