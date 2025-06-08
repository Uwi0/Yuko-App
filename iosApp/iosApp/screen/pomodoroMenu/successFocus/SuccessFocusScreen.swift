
import SwiftUI

struct SuccessFocusScreen: View {
    
    let onFinish: () -> Void
    let onBreak: () -> Void
    
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            Text("Wow.. it's really great to be able to focus all this time, do you want to take a break or finish ?")
            FilledButtonView(
                onClick: onBreak,
                content: {
                    Text("Take a break")
                }
            )
            OutlinedContentButtonView(
                onClick: onFinish,
                content: {
                    Text("Finish")
                }
            )
        }
        
    }
}

#Preview {
    SuccessFocusScreen(onFinish: {}, onBreak: {})
}
