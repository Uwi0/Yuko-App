import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class PomodoroViewModel: ObservableObject {
    
    @Published var uiState: PomodoroState = PomodoroState.companion.default()
    
    private let viewModel: PomodoroViewModelKT = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    
    func initData() {
        observeUiState()
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
    
    deinit {
        stateCancellable?.cancel()
    }
    
}

