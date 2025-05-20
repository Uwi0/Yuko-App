import SwiftUI

struct PomodoroScreen: View {
    
    @StateObject private var viewModel = PomodoroViewModel()
    @ObservedObject private var timerService = TimerService()
    
    var body: some View {
        VStack {
            Text("Hello world \(viewModel.uiState.currentTime)")
            Button(action: startTimer){
                Text("Start")
            }
            Button(action: stopTimer){
                Text("Stop")
            }
        }
        .onAppear {
            viewModel.initData()
        }
    }
    
    private func startTimer() {
        timerService.remainingTime = 100
        timerService.startTimer(
            onTick: { time in
                viewModel.handle(event: .ChangeTime(time: String(time)))
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
