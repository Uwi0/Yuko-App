import SwiftUI
import Shared

struct AddHabitScren: View {
	
	@Binding var state: AddHabitState
	let onEvent: (AddHabitEvent) -> Void
	
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
			title: "Add Habit",
			actionContent: {
				TopBarActionButton(
					imageName: "square.and.arrow.down",
					onClick: { onEvent(.SaveHabit()) }
				)
			},
			onAction: { onEvent(.NavigateBack())}
		)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		VStack(spacing: 16) {
			FieldNameAndDescriptionView(state: $state, onEvent: onEvent)
			Toggle(
				"Is Good Habit ?",
				isOn: Binding(
					get: { state.isToggleOn },
					set: { _ in onEvent(.ToggleType()) }
				)
			)
			TargetQuantityView()
			Spacer()
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 24)
		.frame(maxWidth: 700, alignment: .leading)
	}
	
	@ViewBuilder
	private func TargetQuantityView() -> some View {
		HStack {
			Text("Daily Goal")
			Spacer()
			ButtonQuantityView(quantity: Binding(
				get: { state.targetQuantity },
				set: { onEvent(.QuantityChanged(quantity: Int32($0)))}
			))
		}
	}
	
}

#Preview {
	AddHabitScren(state: .constant(.companion.default()),onEvent: { _ in })
}
