import SwiftUI
import Shared

struct PomodoroScreen: View {
    
    @StateObject private var viewModel = PomodoroViewModel()
    @ObservedObject private var timerService = TimerService()
    
    var body: some View {
        VStack {
            Text("Hello world \(viewModel.uiState.pomodoroTime)")
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
                onDismiss: { viewModel.handle(event: .ShowSheet(show: false)) },
                onStart: { viewModel.handle(event: .StartPomodoro()) }
            )
            .presentationDetents([.height(480)])
        }
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
    }
    
    private func observe(effect: PomodoroEffect?) {
        guard let effect = effect else { return }
        switch onEnum(of: effect) {
        case .startPomodoro(let value): startTimer(time: Int(value.time))
        }
        viewModel.uiEffect = nil
    }
    
    private func startTimer(time: Int) {
        timerService.remainingTime = time
        timerService.startTimer(
            onTick: { time in
                viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
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
