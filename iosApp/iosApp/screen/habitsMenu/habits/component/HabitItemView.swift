import SwiftUI
import Shared

struct HabitItemView: View {
	
	let habit: HabitModel
	
	var body: some View {
		HStack {
			Text(habit.name)
			Spacer()
			TrailingContentView()
		}
		.modifier(BorderedCardModifier())
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
		CustomCheckBox(isSelected: Binding(get: { false }, set: { _ in }))
	}
}

#Preview {
	HabitItemView(habit: dummyHabit)
}
