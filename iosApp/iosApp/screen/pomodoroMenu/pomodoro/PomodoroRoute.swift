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
				SuccessFocusScreen(
					onFinish: { viewModel.handle(event: .FinishPomodoro())},
					onBreak: { viewModel.handle(event: .StartBreak())},
					backToPomodoro: { viewModel.handle(event: .ContinuePomodoro())}
				)
			}
			.fullScreenCover(isPresented: viewModel.uiState.showFailureBinding) {
				FailFocusScreen(
					onRetry: { viewModel.handle(event: .RetryPomodoro()) },
					onFinish: { viewModel.handle(event: .FinishPomodoro()) }
				)
			}
			.fullScreenCover(isPresented: viewModel.uiState.showBreakSuccessBinding) {
				BreakTimeFinishScreen(
					onRetry: { viewModel.handle(event: .RetryPomodoro())},
					onFinish: { viewModel.handle(event: .FinishPomodoro())}
				)
			}
	}
	
	private func observe(effect: PomodoroEffect) {
		switch onEnum(of: effect) {
		case .startPomodoro(let value): startCountDownTimer(time: Int(value.time))
		case .showError(let effect): print("error \(effect.message)")
		case .cancelCountdown: stopCountDownTimer()
		case .cancelPomodoro: stopPomodoroTimer()
		case .finishPomodoro: store.send(.navigateToMain)
		case .startBreak: startBreakTimer()
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
	
	private func startBreakTimer() {
		let duration = Int(viewModel.uiState.durationRestInMinutes)
		pomodoroTimerService.remainingTime = duration
		pomodoroTimerService.startTimer(
			initialTime: duration,
			onTick: { time in
				viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
			},
			onFinish: {
				viewModel.handle(event: .FinishBreak())
			}
		)
	}
	
	private func stopPomodoroTimer() {
		pomodoroTimerService.stopTimer()
		pomodoroTimerService.remainingTime = 0
	}
}
