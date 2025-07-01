import SwiftUI

struct TopBarActionButton: View {
	
	let imageName: String
	let onClick: () -> Void
	
	var body: some View {
		Image(systemName: imageName)
			.resizable()
			.scaledToFit()
			.frame(width: 24, height: 24)
			.onTapGesture {
				onClick()
			}
	}
}

#Preview {
	TopBarActionButton(imageName: "pencil", onClick: {})
}
