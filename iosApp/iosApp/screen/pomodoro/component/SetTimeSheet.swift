import SwiftUI

struct SetTimeSheet: View {
    
    @Binding var focusDuration: Double
    @Binding var shortRestDuration: Double
    @Binding var numberOfCyles: Double
    var onDismiss: () -> Void
    var onStart: () -> Void
    
    private let step = 5.0
    
    var body: some View {
        VStack(spacing: 16) {
            HeaderComponent()
            Spacer().frame(height: 20)
            TimeComponent()
            Spacer().frame(height: 48)
            FilledButtonView(onClick: onStart, content: { Text("Start")})
            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
    }
    
    @ViewBuilder
    private func HeaderComponent() -> some View {
        HStack(alignment: .center) {
            Image(systemName: "xmark.circle")
                .resizable()
                .frame(width: 24, height: 24)
                .onTapGesture {
                    onDismiss()
                }
            Spacer()
            Text("Pomodoro")
            Spacer()
            Spacer().frame(width: 24)
        }
    }
    
    @ViewBuilder
    private func TimeComponent() -> some View {
        VStack(spacing: 8) {
            FocusTimeComponent()
            ShortRestComponent()
            NumberOfCyclesComponent()
        }
    }
    
    @ViewBuilder
    private func FocusTimeComponent() -> some View {
        VStack(alignment: .leading) {
            HStack {
                Text("Focus duration: ")
                Text("\(Int(focusDuration)) m")
            }
            
            Slider(
                value: $focusDuration,
                in: 5...180,
                step: step,
                onEditingChanged: { _ in }
            )
        }
    }
    
    @ViewBuilder
    private func ShortRestComponent() -> some View {
        VStack(alignment: .leading) {
            Text("Short rest duration: \(Int(shortRestDuration)) m")
            Slider(
                value: $shortRestDuration,
                in: 5...45,
                step: step,
                onEditingChanged: { _ in }
            )
        }
    }
    
    @ViewBuilder
    private func NumberOfCyclesComponent() -> some View {
        VStack(alignment: .leading) {
            Text("Number of Cycles: \(Int(numberOfCyles))")
            Slider(
                value: $numberOfCyles,
                in: 1...10,
                onEditingChanged: { _ in }
            )
        }
    }
}

#Preview {
    SetTimeSheet(
        focusDuration: .constant(10),
        shortRestDuration: .constant(5),
        numberOfCyles: .constant(3),
        onDismiss: {},
        onStart: {}
    )
}
