import SwiftUI
import ComposableArchitecture
import Shared

struct PomodoroRoute: View {
			
	@Bindable var store: StoreOf<PomodoroFeature>
    
    @StateObject private var viewModel = PomodoroViewModel()
    @ObservedObject private var pomodoroTimerService = BackgroundTimerService()
    @ObservedObject private var countdownTimerService = TimerService()
    
    var body: some View {
        PomodoroScreen(uiState: $viewModel.uiState, onEvent: viewModel.handle(event:))
            .onAppear {
                viewModel.initData()
            }
            .onChange(of: viewModel.uiEffect) {
                observe(effect: viewModel.uiEffect)
            }
    }
    
    private func observe(effect: PomodoroEffect?) {
        guard let effect = effect else { return }
        switch onEnum(of: effect) {
        case .startPomodoro(let value): startCountDownTimer(time: Int(value.time))
        case .showError(let effect): print("error \(effect.message)")
        case .cancelCountdown: stopCountDownTimer()
        case .cancelPomodoro: stopPomodoroTimer()
        case .showSuccess: print("Success")
        case .showFail: print("Failure")
        }
        viewModel.uiEffect = nil
    }
    
    private func startCountDownTimer(time: Int) {
        countdownTimerService.remainingTime = 5
        countdownTimerService.startTimer(
            onTick: { time in
                viewModel.handle(event: .ChangeCountDownTime(time: String(time)))
            },
            onFinish: {
                startPomodoroTimer(time: time)
            }
        )
    }
    
    private func stopCountDownTimer() {
        countdownTimerService.stopTimer()
        countdownTimerService.remainingTime = 0
    }
    
    private func startPomodoroTimer(time: Int, status: SessionType = .start) {
        viewModel.handle(event: .ChangeStatus(status: status))
        pomodoroTimerService.remainingTime = time
        pomodoroTimerService.startTimer(
            initialTime: time,
            onTick: { time in
                viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
            },
            onFinish: {
                viewModel.handle(event: .SaveProgress(isSuccess: true))
            }
        )
    }
    
    private func stopPomodoroTimer() {
        pomodoroTimerService.stopTimer()
        pomodoroTimerService.remainingTime = 0
    }
}
