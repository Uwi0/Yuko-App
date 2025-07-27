import SwiftUI

struct ButtonQuantityView: View {
	
	@Binding var quantity: Int
	
	private var formattedQuantity: String {
		if quantity == 0 {
			"0"
		} else {
			String(format: "%02d", quantity)
		}
	}
	
	var body: some View {
		HStack(spacing: 16) {
			Button {
				if quantity != 0 {
					quantity -= 1
				}
			} label: {
				Image(systemName: "minus")
					.resizable()
					.frame(width: 24, height: 3)
					.foregroundStyle(ColorTheme.primary)
			}
			
			Text("\(formattedQuantity)")
				.font(Typography.titleLarge)
			
			Button {
				quantity += 1
			} label: {
				Image(systemName: "plus")
					.resizable()
					.frame(width: 24, height: 24)
					.scaledToFit()
					.foregroundStyle(ColorTheme.primary)
			}
		}
	}
	
}

#Preview {
	@Previewable @State var quantity: Int = 0
	ButtonQuantityView(quantity: $quantity)
}
