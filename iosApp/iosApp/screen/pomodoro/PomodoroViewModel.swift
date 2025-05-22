import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class PomodoroViewModel: ObservableObject {
    
    @Published private(set) var uiState: PomodoroState = PomodoroState.companion.default()
    @Published var uiEffect: PomodoroEffect? = nil
    
    private let viewModel: PomodoroViewModelKT = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    func initData() {
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
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: PomodoroEffect) {
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
    
}

