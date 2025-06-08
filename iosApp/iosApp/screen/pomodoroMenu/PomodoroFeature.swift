import Foundation
import ComposableArchitecture

enum SessionStatus {
    case idle
    case breakTime
}

enum PomodoroMenuRoute: Equatable, Identifiable {
    var id: UUID {
        switch self {
        case let .success(id): id
        case let .fail(id): id
        }
    }
    
    case success(id: UUID)
    case fail(id: UUID)
}

@Reducer
struct PomodoroFeature {
    
    @ObservableState
    struct State: Equatable {
        var route: PomodoroMenuRoute? = nil
        var status: SessionStatus = .idle
    }
    
    enum Action: Equatable {
        case timerCompleted
        case timerFailed
        case routeDismissed
        case tapBreak
        case tabBackToMainMenu
    }
    
    var body: some ReducerOf<Self> {
        Reduce { state, action in
            switch action {
            case .timerCompleted:
                state.route = .success(id: UUID())
                state.status = .idle
                print("timer complete")
                return .none
            case .timerFailed:
                state.route = .fail(id: UUID())
                state.status = .idle
                return .none
            case .routeDismissed:
                state.route = nil
                return .none
            case .tapBreak:
                state.route = nil
                state.status = .breakTime
                print("break clicked")
                return .none
            case .tabBackToMainMenu:
                state.route = nil
                return .none
            }
        }
    }
}
