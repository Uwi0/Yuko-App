import SwiftUI
import Shared

struct HabitItemView: View {
	
	private let habit: HabitItemModel
	private let onEvent: (HabitsEvent) -> Void
	@State private var completionCount: Int = 0
	
	init(habit: HabitItemModel, onEvent: @escaping (HabitsEvent) -> Void) {
		self.habit = habit
		self.onEvent = onEvent
		_completionCount = State(initialValue: habit.completionCount)
	}
	
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
			CircularProgressButton(
				progress: $completionCount,
				target: habit.targetFrequency,
				onIncrementValue: { onEvent(.TrackCompletion(model: habit)) }
			)
		}
	}
}

#Preview {
	HabitItemView(habit: dummyHabit, onEvent: { _ in })
}
