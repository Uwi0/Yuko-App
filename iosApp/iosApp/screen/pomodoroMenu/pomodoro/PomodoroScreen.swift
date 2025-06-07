import SwiftUI
import ComposableArchitecture
import Shared

struct PomodoroScreen: View {
    
    @Binding var uiState: PomodoroState
    let onEvent: (PomodoroEvent) -> Void
    
    var body: some View {
        VStack {
            TopContentView()
            Spacer()
            TimeCountDownComponent()
            Spacer().frame(height: 48)
            StartButton()
            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
        .sheet(isPresented: Binding(
            get: { uiState.showSheet},
            set: { shown in onEvent(.ShowSheet(show: shown))})
        ){
            SetTimeSheet(
                focusDuration: Binding(
                    get: { uiState.focusDuration },
                    set: { duration in onEvent(.ChangeFocusTime(time: duration)) }),
                shortRestDuration: Binding(
                    get: { uiState.shortRestDuration},
                    set: { duration in onEvent(.ChangeShortRestTime(time: duration)) }),
                numberOfCyles: Binding(
                    get: { uiState.numberOfCycles},
                    set: { cycle in onEvent(.SetNumberOfCycles(number: cycle)) }),
                onDismiss: { onEvent(.ShowSheet(show: false)) },
                onStart: { onEvent(.SaveSettings()) }
            )
            .presentationDetents([.height(480)])
        }
        .alert(
            isPresented: Binding(
                get: { uiState.showAlert },
                set: { shown in onEvent(.ShowAlert(shown: shown))}),
            
        ) {
            Alert(
                title: Text("Are you sure want to give up?"),
                message: Text("Yuko will sad if you do that"),
                primaryButton: .destructive(
                    Text("Confirm"),
                    action: {
                        onEvent(.SaveProgress(isSuccess: false))
                    }
                ),
                secondaryButton: .cancel()
            )
        }
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        HStack {
            PointItem()
            Spacer()
        }
    }
    
    @ViewBuilder
    private func PointItem() -> some View {
        HStack(spacing: 8) {
            Image(systemName: "heart.fill")
            Text("\(uiState.pointEarned)")
        }
    }
    
    @ViewBuilder
    private func TimeCountDownComponent() -> some View {
        HStack(alignment: .center, spacing: 8) {
            Image(systemName: "tag")
                .resizable()
                .frame(width: 16, height: 16)
            Text("Study: ")
                .font(Typography.titleSmall)
            Text(uiState.pomodoroTime)
                .font(Typography.titleMedium)
            Image(systemName: "chevron.forward")
                .resizable()
                .scaledToFit()
                .frame(width: 16, height: 16)
        }
        .onTapGesture { onEvent( .ShowSheet(show: true)) }
    }
    
    @ViewBuilder
    private func StartButton() -> some View {
        let onCklick: () -> Void = {
            if uiState.status == .focus {
                onEvent(.StartPomodoro())
            } else {
                onEvent(.CancelTimer())
            }
        }
        
        let title = switch uiState.status {
        case .start: "Cancel"
        case .breakTime: "Start"
        case .countDown: "Cancel \(uiState.countDownTime)"
        case .focus: "Break"
        }
        
        FilledButtonView(
            onClick: onCklick,
            content: { Text(title)}
        )
        .frame(width: 120)
    }
}

#Preview {
    let uiState = PomodoroState.companion.default()
    PomodoroScreen(uiState: .constant(uiState), onEvent: { _ in } )
}
