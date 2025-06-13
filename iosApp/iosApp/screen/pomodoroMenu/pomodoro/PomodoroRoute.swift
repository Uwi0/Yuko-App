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
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublisher) { effect in
				observe(effect: effect)
			}
			.fullScreenCover(isPresented: viewModel.uiState.showSuccessBinding) {
				Text("Success")
			}
			.fullScreenCover(isPresented: viewModel.uiState.showFailureBinding) {
				Text("Fail")
			}
	}
	
	private func observe(effect: PomodoroEffect) {
		switch onEnum(of: effect) {
		case .startPomodoro(let value): startCountDownTimer(time: Int(value.time))
		case .showError(let effect): print("error \(effect.message)")
		case .cancelCountdown: stopCountDownTimer()
		case .cancelPomodoro: stopPomodoroTimer()
		}
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
