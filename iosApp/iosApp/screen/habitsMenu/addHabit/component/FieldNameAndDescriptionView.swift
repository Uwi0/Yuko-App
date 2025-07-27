import SwiftUI
import Shared

struct FieldNameAndDescriptionView: View {
	
	@Binding var state: AddHabitState
	let onEvent: (AddHabitEvent) -> Void
	
	var body: some View {
		VStack(spacing: 16) {
			TextField(
				"Name",
				text: Binding(
					get: { state.name },
					set: { value in onEvent(.NameChanged(name: value))}
				)
			)
			.font(Typography.titleLarge)
			TextField(
				"Description",
				text: Binding(
					get: { state.description_ },
					set: { value in onEvent(.DescriptionChanged(description: value))}
				)
			)
			.font(Typography.bodyMedium)
		}
	}
}

#Preview {
	FieldNameAndDescriptionView(
		state: .constant(.companion.default()),
		onEvent: { _ in }
	)
}
