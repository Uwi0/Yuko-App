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
			CompletionCheckBoxView()
		} else {
			Text("\(habit.lastSlipDate) days clean")
		}
	}
	
	@ViewBuilder
	private func CompletionCheckBoxView() -> some View {
		if habit.completionType == .single {
			CustomCheckBox(isSelected: Binding(
				get: { habit.isCompleteToday },
				set: { checked in onEvent(.CheckedGoodHabit(id: habit.habitId, isChecked: checked))
				}
			))
		} else {
			Text("\(habit.targetFrequency)")
		}
	}
}

#Preview {
	HabitItemView(habit: dummyHabit, onEvent: { _ in })
}
