import Foundation
import Shared
import ComposableArchitecture

final class TimerCoordinator {
	private let pomodoroTimer = BackgroundTimerService()
	private let countdownTimer = TimerService()
	private let viewModel: PomodoroViewModel
	private let store: StoreOf<PomodoroFeature>
	
	init(viewModel: PomodoroViewModel, store: StoreOf<PomodoroFeature>) {
		self.viewModel = viewModel
		self.store = store
	}
	
	func observe(effect: PomodoroEffect) {
		switch onEnum(of: effect) {
		case .startPomodoro(let value): startCountDownTimer(time: Int(value.time))
		case .showError(let effect): print("error \(effect.message)")
		case .cancelCountdown: stopCountdownTimer()
		case .cancelPomodoro: stopPomodoroTimer()
		case .finishPomodoro: store.send(.navigateToMain)
		case .startBreak: startBreakTimer()
		}
	}
	
	private func startCountDownTimer(time: Int) {
		countdownTimer.remainingTime = 5
		countdownTimer.startTimer(
			initialTime: 5,
			sessionType: "Count Down",
			interval: 1.0,
			onTick: { [weak self] time in
				self?.viewModel.handle(event: .ChangeCountDownTime(time: String(time)))
			},
			onFinish: { [weak self] in
				self?.startPomodoroTimer(time: time)
			}
		)
	}
	
	private func startPomodoroTimer(time: Int) {
		viewModel.handle(event: .ChangeStatus(status: .start))
		pomodoroTimer.remainingTime = time
		pomodoroTimer.startTimer(
			initialTime: time,
			sessionType: "Focus",
			interval: 1.0,
			onTick: { [weak self] time in
				self?.viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
			},
			onFinish: { [weak self] in
				self?.viewModel.handle(event: .SaveProgress(isSuccess: true))
			}
		)
	}
	
	private func startBreakTimer() {
		let duration = Int(viewModel.uiState.durationRestInMinutes)
		pomodoroTimer.remainingTime = duration
		pomodoroTimer.startTimer(
			initialTime: duration,
			sessionType: "Break Time",
			interval: 1.0,
			onTick: { [weak self] time in
				self?.viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
			},
			onFinish: { [weak self] in
				self?.viewModel.handle(event: .FinishBreak())
			}
		)
	}
	
	private func stopCountdownTimer() {
		countdownTimer.stopTimer()
		countdownTimer.remainingTime = 0
	}
	
	private func stopPomodoroTimer() {
		pomodoroTimer.stopTimer()
		pomodoroTimer.remainingTime = 0
	}
}
