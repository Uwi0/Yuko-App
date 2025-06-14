import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class PomodoroViewModel: ObservableObject {
	
	@Published var uiState: PomodoroState = PomodoroState.companion.default()
	
	private let viewModel: PomodoroViewModelKT = Koin.shared.get()
	private let effectSubject = PassthroughSubject<PomodoroEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	var effectPublisher: AnyPublisher<PomodoroEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	func initData() {
		viewModel.doInitData()
		observeUiState()
		observeEffects()
	}
	
	func handle(event: PomodoroEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeUiState() {
		let publihser = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] state in
			self?.update(state: state)
		}
	}
	
	private func update(state: PomodoroState) {
		DispatchQueue.main.async {
			self.uiState = state
		}
	}
	
	private func observeEffects() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser.sink { completion in
			print("completion \(completion)")
		} receiveValue: { [weak self] effect in
			self?.send(effect: effect)
		}
	}
	
	private func send(effect: PomodoroEffect) {
		DispatchQueue.main.async {
			self.effectSubject.send(effect)
		}
	}
	
	deinit {
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
	
}

