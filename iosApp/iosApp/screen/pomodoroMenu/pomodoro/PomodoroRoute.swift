import SwiftUI
import ComposableArchitecture
import Shared

struct PomodoroRoute: View {
	@Bindable var store: StoreOf<PomodoroFeature>
	
	@StateObject private var viewModel: PomodoroViewModel
	private let coordinator: TimerCoordinator

	init(store: StoreOf<PomodoroFeature>) {
		self.store = store
		let viewModel = PomodoroViewModel()
		self._viewModel = StateObject(wrappedValue: viewModel)
		self.coordinator = TimerCoordinator(
			viewModel: viewModel,
			store: store
		)
	}

	var body: some View {
		PomodoroScreen(uiState: $viewModel.uiState, onEvent: viewModel.handle(event:))
			.task {
				viewModel.initData()
			}
			.onReceive(viewModel.effectPublisher) { effect in
				coordinator.observe(effect: effect)
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
}
