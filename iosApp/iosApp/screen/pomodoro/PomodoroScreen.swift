import SwiftUI

struct PomodoroScreen: View {
    
    @StateObject private var viewModel = PomodoroViewModel()
    @ObservedObject private var timerService = TimerService()
    
    var body: some View {
        VStack {
            Text("Hello world \(viewModel.uiState.focusDuration)")
            Button(action: { viewModel.handle(event: .ShowSheet(show: true))}){
                Text("Start")
            }
            Button(action: stopTimer){
                Text("Stop")
            }
        }
        .onAppear {
            viewModel.initData()
        }
        .sheet(isPresented: Binding(
            get: { viewModel.uiState.showSheet},
            set: { shown in viewModel.handle(event: .ShowSheet(show: shown))})
        ){
            SetTimeSheet(
                focusDuration: Binding(
                    get: { viewModel.uiState.focusDuration },
                    set: { duration in viewModel.handle(event: .ChangeFocusTime(time: duration)) }),
                shortRestDuration: Binding(
                    get: { viewModel.uiState.shortRestDuration},
                    set: { duration in viewModel.handle(event: .ChangeShortRestTime(time: duration)) }),
                numberOfCyles: Binding(
                    get: { viewModel.uiState.numberOfCycles},
                    set: { cycle in viewModel.handle(event: .SetNumberOfCycles(number: cycle)) }),
                onDismiss: { viewModel.handle(event: .ShowSheet(show: false)) }
            )
            .presentationDetents([.medium])
        }
        
    }
    
    private func startTimer() {
        timerService.remainingTime = 100
        timerService.startTimer(
            onTick: { time in
                viewModel.handle(event: .ChangePomodoroTime(time: String(time)))
            },
            onFinish: {}
        )
    }
    
    private func stopTimer() {
        timerService.stopTimer()
        timerService.remainingTime = 0
    }
}             

#Preview {
    PomodoroScreen()
}
