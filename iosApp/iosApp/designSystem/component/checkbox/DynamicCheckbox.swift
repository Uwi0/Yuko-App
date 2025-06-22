import SwiftUI

struct DynamicCheckbox: View {
	@Binding var isChecked: Bool
	
    var body: some View {
			#if os(macOs)
			Toggle(isOn: $isChecked){
				
			}
				.toggleStyle(.checkbox)
			#else
			HStack {
				
			}
			#endif
    }
}

#Preview {
	DynamicCheckbox(isChecked: .constant(true))
}
