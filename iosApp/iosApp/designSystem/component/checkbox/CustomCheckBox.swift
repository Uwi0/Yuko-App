import SwiftUI

struct CustomCheckBox: View {
	
	@Binding var isSelected: Bool
	
	private var tintColor: Color {
		isSelected ? ColorTheme.primary : ColorTheme.secondary
	}
	
	private var icon: String {
		isSelected ? "checkmark.circle" : "circle"
	}
	
	var body: some View {
		Button(action: { isSelected.toggle() }) {
			Image(systemName: icon)
				.resizable()
				.frame(width: 24, height: 24)
				.foregroundColor(tintColor)
		}
		.buttonStyle(.plain)
	}
}
