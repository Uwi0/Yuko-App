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
		stateCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				self?.uiState = state
			}
	}
	
	private func observeEffects() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
	
}

