import SwiftUI
import Shared

struct HabitItemView: View {
	
	let habit: HabitItemModel
	let onEvent: (HabitsEvent) -> Void
	
	var body: some View {
		HStack {
			Text(habit.name)
			Spacer()
			TrailingContentView()
		}
		.modifier(BorderedCardModifier())
		.onTapGesture {
			onEvent(.TappedHabit(id: habit.habitId, type: habit.habitType))
		}
	}
	
	@ViewBuilder
	private func TrailingContentView() -> some View {
		if habit.isGoodHabit {
			CheckBoxView()
		} else {
			Text("\(habit.lastSlipDate) days clean")
		}
	}
	
	@ViewBuilder
	private func CheckBoxView() -> some View {
		CustomCheckBox(isSelected:
			Binding(
				get: { habit.isCompleteToday },
				set: { checked in
					onEvent(.CheckedGoodHabit(id: habit.habitId, isChecked: checked))
				}
			)
		)
	}
}

#Preview {
	HabitItemView(habit: dummyHabit, onEvent: { _ in })
}
